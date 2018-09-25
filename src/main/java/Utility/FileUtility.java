package Utility;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

/**
 * commons-lang3-3.8.jar
 */
public class FileUtility {
    public static final Charset ANSI = Charset.forName("Windows-1252");
    public static final Charset MS950 = Charset.forName("MS950");
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    private Charset readLanguage = MS950;
    private Charset writeLanguage = MS950;

    public Charset getReadLanguage() {
        return readLanguage;
    }

    public void setReadLanguage(Charset readLanguage) {
        this.readLanguage = readLanguage;
    }

    public Charset getWriteLanguage() {
        return writeLanguage;
    }

    public void setWriteLanguage(Charset writeLanguage) {
        this.writeLanguage = writeLanguage;
    }

    /**
     * 開啟檔案
     *
     * @param fileName 檔案名稱(含路徑)
     * @return
     * @throws FileNotFoundException
     */
    public BufferedReader read(String fileName) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(fileName), readLanguage));
    }

    /**
     * 開啟檔案並回傳全部內文
     *
     * @param fileName 檔案名稱(含路徑)
     * @return
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public String readAll(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = read(fileName);
        String line = br.readLine();
        while (StringUtils.isNotEmpty(line)) {
            sb.append(line).append("\r\n");
            line = br.readLine();
        }

        return sb.toString();
    }

    /**
     * 寫入檔案
     *
     * @param fileName 檔案名稱(含路徑)
     * @param value    檔案內容
     * @param append   {true:新增,false:覆寫}
     * @throws IOException
     */
    public boolean write(String fileName, String value, boolean append) throws IOException {
        boolean result = true;
        File newTxt = new File(fileName);
        // 檢查檔案是否已存在
        if (!newTxt.exists()) {
            // 建立目錄、檔案
            result = newTxt.getParentFile().mkdirs() && newTxt.createNewFile();
        }

        if (result) {
            /*
             * append true : 新增 false : 覆寫
             */
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newTxt.getAbsolutePath(), append), writeLanguage));
            // 取得 data
            writer.write(value);
            writer.flush();
            writer.close();
        }

        return result;
    }

    public Path copy(String source, String dest, CopyOption... options) throws IOException {
        // String to = "";'
        String ref = "\\";
        if (dest.contains("/")) {
            ref = "/";
        }
        Path to = Paths.get(dest.substring(0, dest.lastIndexOf(ref)));
        if (!Files.exists(to)) {
            // 建立目錄
            Files.createDirectories(to);
        }

        return Files.copy(new File(source).toPath(), new File(dest).toPath(), options);
    }
}

