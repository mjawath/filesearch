package model;

/**
 * Created by jawa on 12/21/2020.
 */
public class Todo{
    String filePath;
    String line;
    int lineNo;

    public Todo( String filePath,int lineNo, String line) {
        this.lineNo = lineNo;
        this.filePath = filePath;
        this.line = line;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }
}