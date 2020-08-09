import VanityWalletGenerator from './vanity-wallet-generator';
import VanityWalletStorer from './vanity-wallet-storer';
import WalletAPI from './wallet-api';

const express = require('express');
const app = express();
const SERVICE_ENDPOINT = "http://csc-vanity-wallet-service:8080/wallet";
const MINIMUM_WORD_LENGTH = 4;

let CasinocoinAPI = require('casinocoin-libjs').CasinocoinAPI;
let cscApi = new CasinocoinAPI({ server: 'wss://ws01.casinocoin.org:4443' });
let walletApi = new WalletAPI(cscApi);
let words = require('an-array-of-english-words');
let request = require('request');

let vanityWalletGenerator = new VanityWalletGenerator(walletApi, words, MINIMUM_WORD_LENGTH);
let vanityWalletStorer = new VanityWalletStorer(SERVICE_ENDPOINT, vanityWalletGenerator, request);

console.log("CasinoCoin Vanity Wallet Generator v1.0.0, by Danny Noam");

app.get('/liveness',(req,res)=> {
    res.status(200);
});

app.listen(8080, '0.0.0.0');

vanityWalletStorer.generateAndStoreVanityWallets();

