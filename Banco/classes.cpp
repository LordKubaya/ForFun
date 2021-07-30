#include <string>
#include <list>

using namespace std;

class Person{
    private:
        string name;
        string nacionality;
        int _idNumber;
        int age;
};

class BankUser{
    private:
        Person owner;
        Person secoundLegalyPerson;
        list<BankingAccount> accounts;
};

class BankingAccount{
    private:
        string name;
        string accountCode;
        list<Transaction> transactions;
        int _ammount;
};

class Transaction{
    private:
        string typeOfTransaction;
        string _toAccount;
        string _toIdBank;
        string _fromAccount;
        string _fromIdBank;
        int _ammount;
};