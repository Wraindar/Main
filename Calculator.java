import java.io.IOException;
import java.util.TreeMap;
import java.util.Scanner;
class Main {
    static class Converter {
        TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
        TreeMap<Integer, String> arabicKeyMap = new TreeMap<>();

        public Converter() {
            romanKeyMap.put('I', 1);
            romanKeyMap.put('V', 5);
            romanKeyMap.put('X', 10);
            romanKeyMap.put('L', 50);
            romanKeyMap.put('C', 100);
            arabicKeyMap.put(1, "I");
            arabicKeyMap.put(4, "IV");
            arabicKeyMap.put(5, "V");
            arabicKeyMap.put(9, "IX");
            arabicKeyMap.put(10, "X");
            arabicKeyMap.put(40, "XL");
            arabicKeyMap.put(50, "L");
            arabicKeyMap.put(90, "XC");
            arabicKeyMap.put(100, "C");
        }

        boolean isRoman(String number) {
            return romanKeyMap.containsKey(number.charAt(0));
        }

        String intToRoman(int number) {
            StringBuilder roman = new StringBuilder();
            int arabicKey;
            do {
                arabicKey = arabicKeyMap.floorKey(number);
                roman.append(arabicKeyMap.get(arabicKey));
                number -= arabicKey;
            } while (number != 0);
            return roman.toString();
        }

        public int romanToInt(String s) {
            int end = s.length() - 1;
            char[] arr = s.toCharArray();
            int arabic;
            int bug = romanKeyMap.get(arr[end]);
            for (int i = end - 1; i >= 0; i--) {
                arabic = romanKeyMap.get(arr[i]);
                if (arabic < romanKeyMap.get(arr[i + 1])) {
                    bug -= arabic;
                } else {
                    bug += arabic;
                }
            }
            return bug;
        }
    }

    public static void main(String[] args) throws NumberFormatException {
        try {
            Converter converter = new Converter();
            Scanner scan = new Scanner(System.in);
            String[] operations = {"+", "-", "*", "/"};
            String[] regexOperations = {"\\+", "-", "\\*", "/"};
            System.out.print("Введите выражение: ");
            String exp = scan.nextLine();
            int operIndex = -1;
            for (int i = 0; i < operations.length; i++) {
                if (exp.contains(operations[i])) {
                    operIndex = i;
                    break;
                }
            }
            String[] data = new String[0];
            if (operIndex == -1) {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Пользователь ввёл некорректное выражение.");
                }

            } else {
                data = exp.split(regexOperations[operIndex]);
                if (data.length > 2) {
                    try {
                        throw new IOException();
                    } catch (IOException e) {
                        System.out.println("Пользователь ввёл неподходящий формат операции.");
                        System.exit(0);
                    }
                }
            }
            if (converter.isRoman(data[0]) == converter.isRoman(data[1])) {
                int a, b;
                boolean isRoman = converter.isRoman(data[0]);
                if (isRoman) {
                    a = converter.romanToInt(data[0]);
                    b = converter.romanToInt(data[1]);
                } else {
                    a = Integer.parseInt(data[0]);
                    b = Integer.parseInt(data[1]);
                }
                if (a > 10 || b > 10 || a < 1 || b < 1) {
                    try {
                        throw new IOException();
                    } catch (IOException e) {
                        System.out.println("Пользователь ввёл число(а) больше 10 или меньше/равное(ые) 0");
                    }
                } else {
                    int result = switch (operations[operIndex]) {
                        case "+" -> a + b;
                        case "-" -> a - b;
                        case "*" -> a * b;
                        default -> a / b;
                    };
                    if (isRoman) {
                        System.out.print(converter.intToRoman(result));
                    } else {
                        System.out.print(result);
                    }
                }
            } else {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Пользователь ввёл числа в разных форматов.");
                }
            }
        }catch (NumberFormatException e){
                System.out.println("Пользователь ввёл неверный формат операции.");}

        }
    }
