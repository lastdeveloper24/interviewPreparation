import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


/* sameple run testcase run

        GetBirthDate getBirthDate = new GetBirthDate();
        Person root = getBirthDate.makeFamilyTree();


        String tDate = "17102025" ;
        Person ans = getBirthDate.getUpcomingBirthDay(tDate, root);

        if(ans!= null){
            System.out.println(ans.name + " " + ans.birthDay);
        }
        else {
            System.out.println("no upcoming birthday this year");
        }
 */

class Person{
    String name;
    String birthDay;
    Person parentA;
    Person parentB;

    Person(String name, String birthDay){
        this.name = name;
        this.birthDay = birthDay;
    }

    Person(String name, String birthDay, Person parentA, Person parentB){
        this.name = name;
        this.birthDay = birthDay;
        this.parentA = parentA;
        this.parentB = parentB;
    }

    public void setParentA(Person parentA) {
        this.parentA = parentA;
    }

    public void setParentB(Person parentB) {
        this.parentB = parentB;
    }


}

public class GetBirthDate {
     PriorityQueue<Person> priorityQueue;
     Person root; // root of the family tree

    public GetBirthDate() {
        //initialize priorityQueue
        priorityQueue = new PriorityQueue<>(new Comparator<Person>() {
            @Override
            public int compare(Person a, Person b) {
                int aDay = Integer.parseInt(a.birthDay.substring(0,2));
                int aMonth = Integer.parseInt(a.birthDay.substring(2,4));

                int bDay = Integer.parseInt(b.birthDay.substring(0,2));
                int bMonth = Integer.parseInt(b.birthDay.substring(2,4));

                if(aMonth!=bMonth){
                    return aMonth-bMonth;
                }
                else{
                    return aDay-bDay;
                }

            }
        });

        //make family Tree
        root = makeFamilyTree();



    }

    //this function will traver the family tree and return the first upcoming birthday after tDate
     Person getUpcomingBirthDay(String tDate, Person root){
        traverse(root);

        int tMonth = Integer.parseInt(tDate.substring(2,4));
        int tDay = Integer.parseInt(tDate.substring(0,2));
        boolean  flag = false;
        Person temp =null;

        //buffer list to feed back the persons which are popped out from the list
        List<Person> lst = new ArrayList<>();

        while(!priorityQueue.isEmpty()) {
            temp = priorityQueue.poll();

            int personMonth = Integer.parseInt(temp.birthDay.substring(2,4));
            int personDay = Integer.parseInt(temp.birthDay.substring(0,2));

            lst.add(temp);
            if(personMonth >tMonth){
                flag = true;
                break;
            }
            else if (personMonth == tMonth){
                if(personDay >=tDay){
                    flag = true;
                    break;
                }
            }

        }
        for( Person ele : lst){
            priorityQueue.add(ele);
        }
        lst.clear();
        if(!flag) return null;
        return temp;
    }

    Person makeFamilyTree(){
        Person root = new Person("Partha", "24081998");
        Person manik = new Person("Manik", "14041950");
        Person mira = new Person("Mira", "16101955");

        Person pranab = new Person("Pranab", "17101966", manik, mira);
        Person lila = new Person("Lila", "01011973");

        root.setParentA(pranab);
        root.setParentB(lila);

        return root;
    }

     void traverse(Person root){
        if(root==null) return ;
        traverse(root.parentA);
        traverse(root.parentB);

        priorityQueue.add(root);
    }

    void printQueue(){
        while(!priorityQueue.isEmpty()){
            Person temp = priorityQueue.poll();

            System.out.println(temp.name+ " " + temp.birthDay);
        }
    }

}
