package tzy.qrecitewords.javabean;

/**
 * Created by tzy on 2016/1/7.
 */
public class Word {

    /**是否读过，1已经读过，0未读过*/
    int isRead;
    /**原单词*/
    String word;
    /**音标*/
    String phonogram;
    /**中文解释*/
    String paraphrase;
    /**熟悉程度 0代表熟悉；1代表不熟悉；2代表很不熟悉*/
    int familiarity;
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getParaphrase() {
        return paraphrase;
    }

    public void setParaphrase(String paraphrase) {
        this.paraphrase = paraphrase;
    }

    public String getPhonogram() {
        return phonogram;
    }

    public void setPhonogram(String phonogram) {
        this.phonogram = phonogram;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(int familiarity) {
        this.familiarity = familiarity;
    }
}
