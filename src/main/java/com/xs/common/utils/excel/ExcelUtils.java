package com.xs.common.utils.excel;

import com.xs.common.utils.ReflectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Excel表格工具类
 *
 * @author xiaotinghao
 */
public class ExcelUtils {

    /**
     * 单元格默认字体
     */
    public static String cellFont = "微软雅黑";

    /**
     * 导出为Excel（有表头）
     *
     * @param excelName   导出的EXCEL名字
     * @param sheetName   导出的SHEET名字 当前sheet数目只为1
     * @param headers     二维数组
     *                    	 [0]：导出表格的表头显示名称
     *                    	 [1]：导出表格数据取值key，即 map.get(key) 对应的 key
     * @param cellFormats 单元格的样式，默认为 1.String left
     *                    	 1.String left; 2.String center; 3.String right;
     *                    	 4.int right; 5.float ###,###.## right; 6.number: #.00% 百分比 right
     * @param cellWidths  单元格的列宽度，默认为 256*14
     * @param data        数据集 List<Map>
     * @param request     Http请求
     * @param response    Http响应
     */
    public static void export(String excelName, String sheetName, String[][] headers, int[] cellFormats,
                              int[] cellWidths, List<Map<String, Object>> data, HttpServletRequest request, HttpServletResponse response) {
        if (headers == null) {
            // 导出空的Excel
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("state", null);
            int columnCount = headers.length;
            // 未指定单元格列宽，则取默认值
            if (cellWidths == null) {
                // 单元格默认宽度
                int columnWidth = 256 * 14;
                cellWidths = new int[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    cellWidths[i] = columnWidth;
                }
            }
            // 未指定单元格数据类型，则取默认值
            if (cellFormats == null) {
                cellFormats = new int[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    cellFormats[i] = 1;
                }
            }
            // 创建一个工作薄
            HSSFWorkbook wb = new HSSFWorkbook();
            // 创建一个sheet
            HSSFSheet sheet = wb.createSheet(StringUtils.isNotEmpty(sheetName) ? sheetName : "Sheet1");
            // 创建表头，如果没有跳过
            int currentRow = 0;
            if (StringUtils.isNotBlank(headers[0][0])) {
                HSSFRow row = sheet.createRow(currentRow);
                // 表头样式
                HSSFCellStyle style = wb.createCellStyle();
                HSSFFont font = wb.createFont();
                font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                font.setFontName(cellFont);
                font.setFontHeightInPoints((short) 11);
                style.setFont(font);
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                for (int i = 0; i < headers.length; i++) {
                    sheet.setColumnWidth(i, cellWidths[i]);
                    HSSFCell cell = row.createCell(i);
                    cell.setCellValue(headers[i][0]);
                    cell.setCellStyle(style);
                }
                currentRow++;
            }
            // 表格主体 解析list
            parseDataToExcel(data, headers, wb, sheet, cellFormats, currentRow);

            // 设置文件名
            String filename = encodeChineseFileName(request, excelName);

            response.setHeader("Content-disposition", filename + ".xls");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            response.setHeader("Pragma", "No-cache");
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                wb.write(os);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            session.setAttribute("state", "open");
        }
    }

    /**
     * 导出为Excel（无表头）
     *
     * @param excelName   导出的EXCEL名字
     * @param sheetName   导出的SHEET名字 当前sheet数目只为1
     * @param keys        一维数组
     *                    	 [0]：导出表格数据取值key，即 map.get(key) 对应的 key
     * @param cellFormats 单元格的样式，默认为 1
     *                    	 1.String left; 2.String center; 3.String right;
     *                    	 4.int right; 5.float ###,###.## right; 6.number: #.00% 百分比 right
     * @param cellWidths  单元格的列宽度，默认为 256*14
     * @param data        数据集 List<Map>
     * @param request     Http请求
     * @param response    Http响应
     */
    public static void export(String excelName, String sheetName, String[] keys, int[] cellFormats,
                              int[] cellWidths, List<Map<String, Object>> data, HttpServletRequest request, HttpServletResponse response) {
        if (keys == null) {
            // 导出空的Excel
        } else {
            int columnCount = keys.length;
            String[][] headers = new String[columnCount][2];
            for (int i = 0; i < keys.length; i++) {
                headers[i][1] = keys[i];
            }
            export(excelName, sheetName, headers, cellFormats, cellWidths, data, request, response);
        }
    }

    /**
     * 解析数据至表格主体
     *
     * @param data        数据集 List<Map>
     * @param headers     二维数组
     *                    	 [0]：导出表格的表头显示名称
     *                    	 [1]：导出表格数据取值key，即 map.get(key) 对应的 key
     * @param wb          Excel对象
     * @param sheet       Excel的Sheet对象
     * @param cellFormats 单元格的样式，默认为 1.String left
     *                    	 1.String left; 2.String center; 3.String right;
     *                    	 4.int right; 5.float ###,###.## right; 6.number: #.00% 百分比 right
     * @param currentRow  当前解析写入行
     */
    public static void parseDataToExcel(List<Map<String, Object>> data, String[][] headers, HSSFWorkbook wb, HSSFSheet sheet, int[] cellFormats, int currentRow) {
        // 表格主体 解析list
        if (data != null) {
            List<HSSFCellStyle> styleList = new ArrayList<>();
            // 列数
            for (int i = 0; i < headers.length; i++) {
                HSSFCellStyle style = wb.createCellStyle();
                HSSFFont font = wb.createFont();
                font.setFontName(cellFont);
                font.setFontHeightInPoints((short) 10);
                style.setFont(font);
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                if (cellFormats[i] == 1) {
                    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                } else if (cellFormats[i] == 2) {
                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                } else if (cellFormats[i] == 3) {
                    style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                    // int类型
                } else if (cellFormats[i] == 4) {
                    // int类型
                    style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                    style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                } else if (cellFormats[i] == 5) {
                    // float类型
                    style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
                    style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                } else if (cellFormats[i] == 6) {
                    // 百分比类型
                    style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
                    style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                }
                styleList.add(style);
            }
            // 行数
            for (Map<String, Object> map : data) {
                HSSFRow row = sheet.createRow(currentRow);
                // 列数
                for (int j = 0; j < headers.length; j++) {
                    HSSFCell cell = row.createCell(j);
                    Object o = map.get(headers[j][1]);
                    if (o == null || "".equals(o)) {
                        cell.setCellValue("");
                    } else if (cellFormats[j] == 4) {
                        // int类型
                        cell.setCellValue((Long.valueOf(o.toString())));
                    } else if (cellFormats[j] == 5 || cellFormats[j] == 6) {
                        // float和百分比类型
                        cell.setCellValue((Double.valueOf(o.toString())));
                    } else {
                        cell.setCellValue(o.toString());
                    }
                    cell.setCellStyle(styleList.get(j));
                }
                currentRow++;
            }
        }
    }

    /**
     * 对文件流输出下载的中文文件名进行转码，屏蔽各种浏览器版本的差异性
     *
     * @param request     Http请求
     * @param fileName    文件名
     * @param replaceName 中文文件名获取异常时的替换名称
     */
    public static String encodeChineseFileName(HttpServletRequest request, String fileName, String replaceName) {
        String result;
        String userAgent = request.getHeader("USER-AGENT");
        if (null != userAgent) {
            // 判断是否为火狐、谷歌、苹果浏览器
            boolean flag = userAgent.contains("Firefox")
                    || userAgent.contains("Chrome")
                    || userAgent.contains("Safari");
            if (flag) {
                try {
                    result = new String((fileName).getBytes(), "ISO8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return replaceName;
                }
            } else {
                // 其他浏览器
                try {
                    result = URLEncoder.encode(fileName.replace("+", "%20"), "UTF8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return replaceName;
                }
            }
        } else {
            result = fileName;
        }
        return result;
    }

    /**
     * 对文件流输出下载的中文文件名进行转码，屏蔽各种浏览器版本的差异性
     *
     * @param request  Http请求
     * @param fileName 文件名
     */
    public static String encodeChineseFileName(HttpServletRequest request, String fileName) {
        return encodeChineseFileName(request, fileName, "errorFileName");
    }

    /**
     * 导出为Excel
     *
     * @param req       Http请求
     * @param resp      Http响应
     * @param list      待导出列表
     * @param tableName 表名称
     * @param arr       二维数组，记录需要导出的字段中，例如：String[][] arr =
     *                  {{"用户", "userName"},
     *                  {"性别", "gender"},
     *                  {"年龄", "age"}};
     * @param <T>       泛型
     */
    public static <T> void export(HttpServletRequest req, HttpServletResponse resp, List<T> list, String tableName, String[][] arr) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(tableName);

        // 设置表头样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFFont fontStyle = wb.createFont();
        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(fontStyle);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 创建表头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        // 设置excel导出表头名称
        for (int i = 0; i < arr.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(arr[i][0]);
            cell.setCellStyle(cellStyle);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 50 / 10);
        }
        try {
            // 将值放入excel中
            setValueIntoExcel(list, wb, sheet, arr);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String fileName = encodeChineseFileName(req, tableName);
        ServletOutputStream out = null;
        try {
            resp.setContentType("application/octet-stream;charset=UTF-8");
            resp.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            out = resp.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Excel的Sheet赋值
     *
     * @param list  待导出的列表
     * @param wb    Excel对象
     * @param sheet Excel的Sheet对象
     */
    private static <T> void setValueIntoExcel(List<T> list, HSSFWorkbook wb, HSSFSheet sheet, String[][] arr) {
        // 设置样式
        HSSFCellStyle cellStyleText = wb.createCellStyle();
        // 文本左右居中
        cellStyleText.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 文本上下居中
        cellStyleText.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 自动换行
        cellStyleText.setWrapText(true);
        // 组装表格数据
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                T t = list.get(i);
                // 创建行，从第二行开始写数据
                HSSFRow row = sheet.createRow(i + 1);
                // 创建单元格
                for (int j = 0; j < arr.length; j++) {
                    String fieldName = arr[j][1];
                    HSSFCell cell = row.createCell(j);
                    cell.setCellStyle(cellStyleText);

                    Object value = ReflectUtils.invokeGet(t, fieldName);
                    if (value == null) {
                        cell.setCellValue("");
                    } else {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }
    }

}
