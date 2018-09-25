package Utility;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author KennyFive05
 * @version 1.0
 * @since 2018/09/15
 * import dom4j-1.6.1.jar
 */
public class XMLUtility {

    /**
     * 取得指定的 Tag 的第一筆資料
     *
     * @param xml
     * @param tags
     * @return
     * @throws DocumentException
     */
    public static String getFirstValue(String xml, String... tags) throws DocumentException {
        String result = "";
        List<String> list = getValue(xml, tags);
        if (list != null && list.size() > 0) {
            result = list.get(0);
        }

        return result;
    }

    /**
     * 取得指定的 Tag 內容, 可指定 Tag 中 Tag
     *
     * @param xml
     * @param tags
     * @return
     * @throws DocumentException
     */
    public static List<String> getValue(String xml, String... tags) throws DocumentException {
        if (tags == null || tags.length < 1)
            return new ArrayList<>();

        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();

        return searchTag(root, true, tags);
    }

    /**
     * 取得指定的 Tag XML內容, 可指定 Tag 中 Tag
     *
     * @param xml
     * @param tags
     * @return
     * @throws DocumentException
     */
    public static List<String> searchTag(String xml, String... tags) throws DocumentException {
        if (tags == null || tags.length < 1)
            return new ArrayList<>();

        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();

        return searchTag(root, false, tags);
    }

    /**
     * 取得指定的 Tag 內容 or XML內容, 可指定 Tag 中 Tag
     *
     * @param root
     * @param isTextOnly
     * @param tags
     * @return
     */
    @SuppressWarnings("unchecked")
    private static List<String> searchTag(Element root, Boolean isTextOnly, String... tags) {
        List<String> list = new ArrayList<>();
        if (tags[0].equals(root.getName())) {
            if (tags.length > 1) {
                String[] array = new String[tags.length - 1];
                System.arraycopy(tags, 1, array, 0, tags.length - 1);
                list.addAll(searchTag(root, isTextOnly, array));
            } else if (!isTextOnly) {
                list.add(root.asXML());
            } else if (root.isTextOnly()) {
                list.add(root.getText());
            }
        }
        Iterator<Element> it = root.elementIterator();
        while (it.hasNext()) {
            Element element = it.next();
            list.addAll(searchTag(element, isTextOnly, tags));
        }

        return list;
    }

    /**
     * 將 xml 自動縮排
     *
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static String format(String xml) throws DocumentException {
        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        return format(root);
    }

    /**
     * 將 xml 自動縮排
     *
     * @param root
     * @return
     */
    public static String format(Element root) {
        return format(0, root);
    }

    /**
     * 將 xml 自動縮排
     *
     * @param root
     * @return
     */
    @SuppressWarnings("unchecked")
    private static String format(int tab, Element root) {
        StringBuilder sb = new StringBuilder();
        sb.append(tab(tab, "<" + root.getName() + ">"));
        tab++;
        Iterator<Element> it = root.elementIterator();
        while (it.hasNext()) {
            Element element = it.next();
            if (element.isTextOnly()) {
                sb.append(tab(tab, element.asXML()));
            } else {
                sb.append(format(tab, element));
            }
        }
        tab--;
        sb.append(tab(tab, "</" + root.getName() + ">"));
        return sb.toString();
    }

    /**
     * 增加縮排
     *
     * @param tab
     * @param str
     * @return
     */
    private static String tab(int tab, String str) {
        for (int i = 0; i < tab; i++) {
            str = String.format("    %s", str);
        }
        return str + "\r\n";
    }
}
