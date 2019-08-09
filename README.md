Disambiguating between similar options using fuzzy string match.

You will need to set a ratio for fuzzy match. 


Example: 

Imagine we have below similar options for user to select one:

1.	Megan ULTIMATE REWARD
2.	LIQUID OWNER CARD        
3.	Finn Debit Card         
4.	Finn Netflix Debit Card        
5.	Finn Credit Card Hamid


If user enter “finn”. Then this script will return options 3,4,5
If user enter “debit”, result will be “3”
If user enter “unlimitedd” (with typo – fuzzy match), result will be “1”
If user enter “test” result will be “1,2,3,4,5”


