import me.xdrop.fuzzywuzzy.FuzzySearch

/**
 * WE HAVE THREE INPUT, WORLDLIST, CARD LIST FROM GWS CALL, ADN USER UTTURENCE
 */

// Create a list of words Make sure word's are not duplicated
def wordList = []



//GET THE USER INFO AND MAKE A LIST
def userInputList = "I lost my hamid card".toLowerCase().split(" ")

//Ratio for String match,100 is exact match
fuzzyStringMatchRatio = 80



//DEFINING THE CARD INFORMATION... WE ALREADY HABE THIS INFO... MOCK DATA
cards = [
        [nickname:"Megan ULTIMATE REWARDS", maskedAccountNumber:047],
        [nickname:"LIQUID OWNER CARD", maskedAccountNumber:4875],
        [nickname:"Finn Debit Card", maskedAccountNumber:00],
        [nickname:"Finn Netflix Debit Card", maskedAccountNumber:00],
        [nickname:"Finn Credit Card Hamid", maskedAccountNumber:00]
]



//GENERATE THE CARD LIST DTA STRUCUTR , LIST IN THE LIST
def cardListExtraced = []
cards.collect(){
    card -> cardListExtraced.add(card.nickname.toString().toLowerCase().split(" "))
}



//ADD THE POTENTIAL NICKNAMES TO THE WORD LIST
cardListExtraced.collect(){
    card -> card.collect(){
        keyWordInCardName -> wordList.add(keyWordInCardName)
    }
}


//Make sure list has unique items and also remove card form the list
wordList.unique()
wordList.remove("card")


//EXTRACT THE KEY WORDS FROM USER ENTRANCE
def keyWords = []
userInputList.collect(){
    word ->
        score =FuzzySearch.extractOne(word.toString(),wordList as List).score
        if ( score >= fuzzyStringMatchRatio){
            keyWords.add(word)
        }
        println "score for " + word + " is " + score
}




//GET THE NUMBER OF CARDS TO BUILD THE NUMBER OF LIST BASED ON THAT
def final numOfKeyWrods = keyWords.size()

print("log.info --> world list: ")
println(wordList)

print("log.info --> User Input List: ")
println(userInputList)

print("log.info --> Key Words: ")
println(keyWords)



//GENERATE DYNAMIC MAP TO SAVING THE RESULT
def resultMap = [:]

0.upto(numOfKeyWrods){
    id -> resultMap.(id.toString()) = []
}


print("log.info --> Dynamic Map result:")
println(resultMap)
print("log.info --> User Card info extracted:")
println(cardListExtraced)


//CALCULATION PART
int cardID = 0;
cardListExtraced.collect() {
    extracteCard ->
        int numOfMach = checkNumOfWordsInCard(extracteCard,keyWords)
        resultMap.(numOfMach.toString()).add (cardID++)

}


println("====================================")
print("log.info --> Result Map: ")
println resultMap


//Call the function to anlize the result and print the card options to user
analyzeResultMap(resultMap)




/**
 *  Check how many words is in ths user card.....
 * @param card
 * @param keyWords
 * @return
 */
int checkNumOfWordsInCard(card ,keyWords){
    int result = 0;
    keyWords.collect(){

        word -> if(FuzzySearch.extractOne(word.toString(),card as List).score >= fuzzyStringMatchRatio) result++
    }
    return result
}


/**
 * Analyze and return the final result. Card selection part
 * @param resultMap
 * @return
 */
def analyzeResultMap(resultMap){

    listIdInMap = resultMap.size() - 1
    while (listIdInMap >= 0){
        def numOfCardInMapEntry = resultMap.(listIdInMap.toString()).size()
        if (numOfCardInMapEntry == 1) {
            print "Selected card is ${resultMap.(listIdInMap.toString())}"
            println(fetchCardNameById(resultMap.(listIdInMap.toString()),cards))
            break
        }
        if (numOfCardInMapEntry > 1 ) {
            println "select between ${resultMap.(listIdInMap.toString())}"
            println(fetchCardNameById(resultMap.(listIdInMap.toString()),cards))
            break
        }
        listIdInMap--

    }
}


/**
 * Get the names from cars map
 * @param idListj
 * @param cards
 * @return
 */
String fetchCardNameById(def idList,def cards) {
    String result =""
    cards.collect(){
        card ->
            if (idList.contains(card.id)) result = result + card.nickname + " AND "
    }
    return result

}


