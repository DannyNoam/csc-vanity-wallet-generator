import VanityWalletGenerator from './vanity-wallet-generator';
import VanityWalletStorer from './vanity-wallet-storer';
import WalletAPI from './wallet-api';

const express = require('express');
const app = express();
const SERVICE_ENDPOINT = "http://csc-vanity-wallet-service:8080/wallet";
const MINIMUM_WORD_LENGTH = 3;

let CasinocoinAPI = require('casinocoin-libjs').CasinocoinAPI;
let cscApi = new CasinocoinAPI({ server: 'wss://ws01.casinocoin.org:4443' });
let walletApi = new WalletAPI(cscApi);
let request = require('request');
let englishWords = require('an-array-of-english-words');
let names = require('human-names');
let badWords = require('badwords-list');

const assembleWordsList = () => {
    let words = [];

    words = addWordsToList(englishWords, words);
    words = addWordsToList(names.allEn, words);
    words = addWordsToList(names.allNl, words);
    words = addWordsToList(names.allEs, words);
    words = addWordsToList(names.allDe, words);
    words = addWordsToList(names.allFr, words);
    words = addWordsToList(names.allIt, words);
    words = addWordsToList(badWords.array, words);
    words = removeNonEnglishAlphabeticCharacters(words);
    words = lowercaseAllWords(words);
    words = removeDuplicateWords(words);
    words = sortWordsIntoAlphabeticalOrder(words);

    return words;
}

const addWordsToList = (words, wordsList) => {
    for (let i = 0; i < words.length; i++) {
        if(words[i].length >= MINIMUM_WORD_LENGTH) {
            wordsList.push(words[i]);
        }
    }

    return wordsList;
}

const removeDuplicateWords = (words) => {
    return Array.from(new Set(words));
}

const sortWordsIntoAlphabeticalOrder = words => {
    words.sort((a, b) => b.length - a.length);

    return words;
}

const removeNonEnglishAlphabeticCharacters = words => {
    const regex = new RegExp('^\s*([0-9a-zA-Z]+)\s*$');

    return words.filter((word) => {
        return word.match(regex);
    });
}

const lowercaseAllWords = words => {
    return words.map((word) => {
        return word.toLowerCase();
    });
}

let words = assembleWordsList();
let vanityWalletGenerator = new VanityWalletGenerator(walletApi, words, MINIMUM_WORD_LENGTH);
let vanityWalletStorer = new VanityWalletStorer(SERVICE_ENDPOINT, vanityWalletGenerator, request);

console.log("CasinoCoin Vanity Wallet Generator v1.0.0, by Danny Noam");
console.log("Total number of vanity words: " + words.length);

app.get('/liveness',(req,res)=> {
    res.status(200);
});

app.listen(8080, '0.0.0.0');

vanityWalletStorer.generateAndStoreVanityWallets();

