import VanityWalletGenerator from './vanity-wallet-generator';
import VanityWalletStorer from './vanity-wallet-storer';

const SERVICE_ENDPOINT = "http://tbc";
const WALLET_BATCH_SIZE = 1;

let CasinocoinAPI = require('casinocoin-libjs').CasinocoinAPI;
let cscApi = new CasinocoinAPI({ server: 'wss://ws01.casinocoin.org:4443' });
let words = require('an-array-of-english-words');
let request = require('request');

let vanityWalletGenerator = new VanityWalletGenerator(cscApi, words);
let vanityWalletStorer = new VanityWalletStorer(SERVICE_ENDPOINT, vanityWalletGenerator, request, WALLET_BATCH_SIZE);

console.log("CasinoCoin Vanity Wallet Generator v1.0.0, by Danny Noam");

while(true) {
  vanityWalletStorer.generateAndStoreVanityWallets();
}

