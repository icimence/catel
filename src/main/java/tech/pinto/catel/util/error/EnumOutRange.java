package tech.pinto.catel.util.error;

public class EnumOutRange extends CustomException {
    public String enumName;
    public String input;

    public EnumOutRange(String enumName, String input) {
        this.enumName = enumName;
        this.input = input;
    }
}
