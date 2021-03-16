import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnpackingString {

    public UnpackingString() {
    }

    boolean StrValidate(String line) {
        if (line.length() == 0) {
            System.out.println("Пустая строка!");
            return false;
        }

        Pattern pattern = Pattern.compile("[\\W&&[^\\[\\]]]");
        Pattern pattern2 = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(line);

        Matcher buff = pattern2.matcher(String.valueOf(line.charAt(line.length() - 1)));
        if (buff.find())
            return false;

        boolean flag = false;//флаг закрытия скобки

        if (matcher.find())
            return false;
        else {
            int chet = 0;
            for (int i = 0; i < line.length(); i++) {

                if (line.charAt(i) == ']' && !flag) {
                    System.out.println("Лишний символ ']'!");
                    return false;
                } else if (line.charAt(i) == ']') {
                    chet--;
                    if (chet == 0)
                        flag = false;
                }
                try {
                    if (i != 0) {
                        Matcher pr = pattern2.matcher(String.valueOf(line.charAt(i - 1)));
                        if (line.charAt(i) == '[' && !pr.find()) {
                            System.out.println("Перед подстрокой должна стоять цифра!");
                            return false;
                        } else if (line.charAt(i) == '[') {
                            if (chet == 0)
                                flag = true;
                            chet++;
                        }
                    }
                    if (i != line.length() - 1) {
                        Matcher pr = pattern2.matcher(String.valueOf(line.charAt(i)));
                        Matcher pr2 = pattern2.matcher(String.valueOf(line.charAt(i + 1)));
                        if ((pr.find() && line.charAt(i + 1) != '[' && !pr2.find())) {
                            System.out.println("Цифры могут стоять только перед подстрокой!");
                            return false;
                        }
                    }
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Перед подстрокой должна стоять цифра!");
                    return false;
                }

            }
            if (chet != 0) {
                System.out.println("Есть не закрытая скобка!");
                return false;
            }
        }
        return true;
    }

    public String Unpacking(String line, String outLine) {
        int KolIter = 0;
        String INT = "";
        String buff = "";
        int chet = 0;
        boolean in = false, multi = false, num;//флаги вложеность,запись числа,символ является числом

        Pattern pattern = Pattern.compile("[0-9]");

        for (int i = 0; i < line.length(); i++) {
            String a = String.valueOf(line.charAt(i));
            Matcher pr = pattern.matcher(a);
            num = pr.find();
            if (in) {
                if (a.equals("]")) {
                    chet--;
                    if (chet == 0) {
                        in = false;
                        for (int j = 0; j < KolIter; j++)
                            outLine = Unpacking(buff, outLine);
                        KolIter = 0;
                        buff = "";
                        continue;
                    }
                } else if (a.equals("["))
                    chet++;
                buff += a;
                continue;
            }
            if (!num && !a.equals("[") && !a.equals("]"))
                outLine += a;
            else if (num && !multi) {
                multi = true;
                INT = "";
                INT += a;
            } else if (num && multi) {
                INT += a;
            } else if (a.equals("[")) {
                chet++;
                KolIter = Integer.parseInt(INT);
                INT = "";
                multi = false;
                in = true;
                continue;
            }


        }
        return outLine;
    }


    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.print("Введите строку - ");
            Scanner in = new Scanner(System.in);
            String line = in.nextLine();
            UnpackingString pack = new UnpackingString();

            if (pack.StrValidate(line)) {
                String outLine = "";
                outLine = pack.Unpacking(line, outLine);
                System.out.println(outLine);
            }
        } else {
            for (int i = 0; i < args.length; i++) {
                UnpackingString pack = new UnpackingString();
                if (pack.StrValidate(args[i])) {
                    String outLine = "";
                    outLine = pack.Unpacking(args[i], outLine);
                    System.out.println(outLine);
                }
            }
        }
    }
}
