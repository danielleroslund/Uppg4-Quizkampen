package ServerSide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class QuestionDatabase {
    //Lista av frågor
    public QuestionDatabase(){
        createCategories();
    }

    private List<Category> category = new ArrayList<>();

    private void createCategories(){
        category.add( //skapar kategorier
                new Category("Historia", Arrays.asList( //skapar en arraylista av frågan och svarsalternativen samt vilket som är rätt svar
                        new Question("Mellan vilka år pågick först världskriget?", "1912 - 1917", "1914 - 1918","1914 - 1919","1913 - 1918", 2),
                        new Question("I vilket land föddes moralfilosofen Adam Smith?", "Skottland", "USA","Irland","Norge", 1),
                        new Question("Vem var Sveriges första statsminister?", "Carl Gustaf", "Carl Johan", "Gustav Vasa", "Louis de Geer ", 4),
                        new Question("Vad kallas den bildskrift som användes av de gamla egypterna?", "Hipoglyfer", "Nileskrift", "Kileskrift", "Hieroglyfer", 4)
                ))
        );
        category.add(
                new Category("Sport", Arrays.asList(
                        new Question("Var hölls sommar-OS år 2016?", "Rio de Janeiro", "London","Peking","Seoul", 1),
                        new Question("Vad heter högsta professionella ligan i England?", "Champions League", "La Liga","Premier League","Serie A", 3),
                        new Question("Vem vann Uefa Champions League 2005/2006?", "AC Milan", "Manchester United", "FC Barcelona", "Chelsea", 3),
                        new Question("Vad heter fotbollsklubben Liverpool FCs hemmaarena?", "Anfield", "Old Trafford", "Highbury", "Wembley", 1)
                ))
        );
        category.add(
                new Category("Film", Arrays.asList(
                        new Question("Vilken är den mest inkomstbringande filmen genom tiderna?", "Avatar", "Avengers: Endgame","Titanic","Frozen", 1),
                        new Question("Vilket år kom filmen \"2012\" ut?", "2012", "2009","2015","2010", 2),
                        new Question("Vad heter filmen som tre ekorrar är med i?", "Smurfarna","Alvin o gänget","Kalle och chokladfabriken","Solsidan", 2),
                        new Question("Vad heter prinsessan i My Little Pony?", "Fluttershy","Pinkie Pie","Spike","Twilight Sparkle", 4)
                ))
        );
        category.add(
                new Category("Geografi", Arrays.asList(
                        new Question("Vad är Brasiliens huvudstad?", "Rio de Janeiro", "São Paulo","Brasilia","Buenos Aires", 3),
                        new Question("Vilken är Europas längsta flod?", "Donau", "Nilen","Kama","Volga", 4),
                        new Question("Vilket land har flest pyramider?", "Egypten", "Sudan", "Mexico", "Sydafrika", 2),
                        new Question("Vad heter huvudstaden i den amerikanska delstaten Alaska?", "Raleigh", "Juneau", "Madison", "Topeka", 2)



                ))
        );
    }
    
    public List<Question> getQuestions(String categoryName){
        // hämtar kategoriera, filterar ut den valda,distinct() ger en ström där varje element bara förekommer en gång, limit() ger strömmen trunkerad till högst n st element
        List<Category> category1 = category.stream().filter( categorys -> categorys.getName().equalsIgnoreCase(categoryName)).distinct().limit(1).collect(toList());

        return category1.get(0).getQuestions();
    }

    public String[] getCategories() { // gör en array av de olika kategorierna
        return category.stream().map(Category::getName).toArray(String[]::new);

    }

}