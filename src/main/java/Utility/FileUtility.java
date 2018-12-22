package Utility;

import Model.FileVo;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        while (line != null) {
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
            newTxt.getParentFile().mkdirs();
            result = newTxt.createNewFile();
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

    /**
     * copy 檔案
     *
     * @param source
     * @param dest
     * @param options
     * @return
     * @throws IOException
     */
    public Path copy(String source, String dest, CopyOption... options) throws IOException {
        source.replace("/","\\");
        dest.replace("/","\\");
        // String to = "";'
        Path to = Paths.get(dest.substring(0, dest.lastIndexOf("\\")));
        if (!Files.exists(to)) {
            // 建立目錄
            Files.createDirectories(to);
        }

        return Files.copy(new File(source).toPath(), new File(dest).toPath(), options);
    }

    public boolean isLivebyDir(String dir) {
        return Files.exists(Paths.get(dir));
    }

    /**
     * 取得有符合檔名的檔案清單
     *
     * @param path       資料夾路徑
     * @param isSeachDir 是否要搜尋子目錄
     * @param filter     要尋找的檔名
     * @param blacklist  黑名單
     * @return
     */
    public List<FileVo> getFileList(String path, boolean isSeachDir, List<String> filter, List<String> blacklist) {
        List<FileVo> result = new ArrayList<>();
        // 目錄資料夾
        List<FileVo> dir = new ArrayList<>();
        // 設定根目錄
        FileVo vo = new FileVo(path, path);
        dir.add(vo);

        for (int i = 0; i < dir.size(); i++) {
            String url = dir.get(i).getPath();
            // 開啟目錄
            java.io.File folder = new java.io.File(url);
            // 檢查是否為目錄
            if (isSeachDir && !folder.isDirectory()) {
                continue;
            }
            // 取得目錄下的清單
            String[] list = folder.list();
            // 空目錄
            if (list == null || list.length == 0)
                continue;

            for (String file : list) {
                // 排除例外清單
                if (isBlacklist(url + file, blacklist)) {
                    continue;
                }

                boolean isDir = true;
                if (filter == null)
                    filter = new ArrayList<>(Arrays.asList(""));
                // 檢查檔名
                for (String tFilter : filter) {
                    if ((url + file).toLowerCase().indexOf(tFilter.toLowerCase()) > -1
                            && (!isSeachDir || !new java.io.File(url + file).isDirectory())) {
                        // 有符合檔名的檔案
                        result.add(new FileVo(file, url));
                        isDir = false;
                        break;
                    }
                }
                // 無符合副檔名的檔案
                if (isSeachDir && isDir) {
                    dir.add(new FileVo(file, url + file + "\\"));
                }
            }
        }

        return result;
    }

    /**
     * 排除例外清單
     *
     * @param file
     * @return
     */
    private boolean isBlacklist(String file, List<String> blacklist) {
        if (blacklist == null)
            return false;

        boolean bool = false;
        for (String tBlacklist : blacklist) {
            if (file.toLowerCase().indexOf(tBlacklist.toLowerCase()) > -1) {
                bool = true;
            }
        }

        return bool;
    }
}

