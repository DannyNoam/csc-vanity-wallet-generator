import VanityWalletGenerator from './vanity-wallet-generator';
import VanityWalletStorer from './vanity-wallet-storer';
import WalletAPI from './wallet-api';

const SERVICE_ENDPOINT = "http://tbc";
const WALLET_BATCH_SIZE = 1;
const MINIMUM_WORD_LENGTH = 3;

let CasinocoinAPI = require('casinocoin-libjs').CasinocoinAPI;
let cscApi = new CasinocoinAPI({ server: 'wss://ws01.casinocoin.org:4443' });
let walletApi = new WalletAPI(cscApi);
let words = require('an-array-of-english-words');
let request = require('request');

let vanityWalletGenerator = new VanityWalletGenerator(walletApi, words, WALLET_BATCH_SIZE, MINIMUM_WORD_LENGTH);
let vanityWalletStorer = new VanityWalletStorer(SERVICE_ENDPOINT, vanityWalletGenerator, request);

console.log("CasinoCoin Vanity Wallet Generator v1.0.0, by Danny Noam");

while(true) {
  vanityWalletStorer.generateAndStoreVanityWallets();
}

