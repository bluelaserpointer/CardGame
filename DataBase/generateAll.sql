set @userAmountWhoOwnSomething = 50;
call deleteAll();
call generateUser();
call generateCard();
call generateItem();
call generateOwnCard();
call generateOwnItem();
call generateMail();
call generateActivity();