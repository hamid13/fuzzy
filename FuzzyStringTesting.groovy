Set values = ["I","want","transfer","to","meghan"]
log.info(getFuzzyScore("megan".toString(),values)


        double getFuzzyScore(String word, Set values1){
    Map<String, Map<String, Double>> map = textService.searchFuzzy(values1,word, 0.75).rowMap();
    def tempMap =  map.get((map.keySet().getAt(0)))
    return tempMap.get(tempMap.keySet().getAt(0))
}

