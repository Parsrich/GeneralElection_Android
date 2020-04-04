package lets.vote.generalelection;

public class PartyVO implements Comparable<PartyVO>{
    String name;
    int number;
    String color;
    String url;

    public PartyVO(String name, String url, int number) {
        this.name = name;
        this.url = url;
        this.number = number;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PartyVO{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", color='" + color + '\'' +
                ", url='" + url + '\'' +
                '}';
    }


    @Override
    public int compareTo(PartyVO o) {
        if (this.number != 0 && o.getNumber() != 0){
            if (this.number < o.getNumber()) {
                return -1;
            } else if (this.number > o.getNumber()) {
                return 1;
            }
        }else if(this.number == 0 && o.getNumber() == 0){
            return this.name.compareTo(o.name);
        }else if(this.number == 0 && o.getNumber() != 0){
            return 1;
        }else if(this.number != 0 && o.getNumber() == 0){
            return -1;
        }else{
            return 0;
        }
        return 0;
    }
}
