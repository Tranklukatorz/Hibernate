package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

/*

     Создание таблицы User(ов)
     Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
     Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
     Очистка таблицы User(ов)
     Удаление таблицы

 */


public class Main {
    public static void main(String[] args) {

        UserServiceImpl myTabInBd = new UserServiceImpl();

        List<User> myAllUser = new ArrayList<>();
        myAllUser.add(new User("Masha", "Petrova", (byte)23));
        myAllUser.add(new User("Vasya", "Petrov", (byte)30));
        myAllUser.add(new User("Petr", "Vasechkin", (byte)17));
        myAllUser.add(new User("Darya", "Subbotina", (byte)32));

        myTabInBd.createUsersTable();
        for(User user : myAllUser){
            myTabInBd.saveUser(user.getName(), user.getLastName(), user.getAge());
            //Из задания не совсем понятно в каком месте организовывать именно этот вывод
            System.out.printf("User с именем – %s добавлен в базу данных\n", user.getName());
        }

        System.out.println();

        List<User> newListUsers = myTabInBd.getAllUsers();
        for (User usr: newListUsers) {
            System.out.println(usr);
        }

        myTabInBd.createUsersTable();
        myTabInBd.dropUsersTable();

    }
}
