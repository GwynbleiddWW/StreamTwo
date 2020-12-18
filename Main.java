package StreamAPI2;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Bruce", "Walter", "Raven", "Adrian", "Magnus", "Oswald", "Wade");
        List<String> families = Arrays.asList("Wayne", "Kovacs", "Darkholme", "Veidt", "Eisenhardt", "Cobblepot", "Wilson");
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            personList.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        long count = personList.stream()
                .filter(x -> x.getAge() < 18)
                .count();
        System.out.println("1.Количество несовершеннолетних: " + count);

        List<String> militaryList = personList.stream()
                .filter(x -> x.getAge() >= 18 && x.getAge() < 27 && x.getSex().equals(Sex.MAN))
                .map(Person::getFamily)
                .limit(10) //Поставил лимит 10, чтобы не грузить систему и нормально видеть результат
                .collect(Collectors.toList());
        System.out.println("2.Список фамилий призывников: " + militaryList);

        List<String> workableHigherList = personList.stream()
                .filter(x -> x.getAge() >= 18)
                .filter(x -> x.getSex().equals(Sex.WOMEN) && x.getAge() < 60 ||
                        x.getSex().equals(Sex.MAN) && x.getAge() < 65)
                .filter(x -> x.getEducation().equals(Education.HIGHER))
                .map(Person::getFamily)
                .limit(10)
                .sorted(Comparator.naturalOrder())// Использовал натуральную сортировку, поскольку у нас список фамилий - String
                .collect(Collectors.toList());
        System.out.println("3.Потенциально работоспособные люди с высшим образованием: " + workableHigherList);
    }
}
