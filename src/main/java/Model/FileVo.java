package Model;

import lombok.Getter;
import lombok.Setter;

public class FileVo {
    @Getter
    @Setter
    private String Name;
    @Getter
    @Setter
    private String Path;

    public FileVo(String name, String path) {
        Name = name;
        Path = path;
    }

    @Override
    public String toString() {
        return "FileVo{" +
                "Name='" + Name + '\'' +
                ", Path='" + Path + '\'' +
                '}';
    }
}
