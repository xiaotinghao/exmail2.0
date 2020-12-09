package com.xs.common.utils.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.xs.common.utils.FileUtils;
import com.xs.common.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import static com.xs.common.constants.NumberConstants.SIXTEEN;

/**
 * XML解析类
 *
 * @author xiaotinghao
 */
@SuppressWarnings("unused")
public class XmlParser {

    /**
     * 获取并解析xml文件内容
     *
     * @param originFilePath 文件路径
     * @return xml文件解析结果
     */
    @SuppressWarnings("unused")
    public static Map<String, String> parse(String originFilePath) {
        return parse(originFilePath, null);
    }

    /**
     * 获取并解析xml文件内容
     *
     * @param originFilePath 文件路径
     * @param backupPath     备份路径
     * @return xml文件解析结果
     */
    public static Map<String, String> parse(String originFilePath, String backupPath) {
        Map<String, String> resultMap = new HashMap<>(SIXTEEN);
        // 解析前先备份文件
        if (!FileUtils.backup(originFilePath, backupPath)) {
            return resultMap;
        }
        // 获取xml文件内容
        String xmlContent = FileUtils.getFileContent(originFilePath);
        // 解析xml文件内容
        try {
            Document document = DocumentHelper.parseText(xmlContent);
            Element root = document.getRootElement();
            @SuppressWarnings("rawtypes")
            Iterator elementIt = root.elementIterator();
            while (elementIt.hasNext()) {
                Element elementElement = (Element) elementIt.next();
                if (elementElement != null) {
                    String elementName = elementElement.getName();
                    if (StringUtils.isNotEmpty(elementName)) {
                        String elementValue = elementElement.getStringValue();
                        if (StringUtils.isNotEmpty(elementValue)) {
                            resultMap.put(elementName, elementValue);
                        }
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

}
