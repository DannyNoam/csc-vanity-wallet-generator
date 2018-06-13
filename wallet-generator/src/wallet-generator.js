// const CasinocoinAPI = require('casinocoin-libjs').CasinocoinAPI
// const api = new CasinocoinAPI({ server: 'wss://ws01.casinocoin.org:4443' })
export default class WalletGenerator {
  
  constructor(api) {
    this.api = api;
  }
  
  generate(numberOfAddresses) {
    var cscAccounts = [];
  
    while (cscAccounts.length < numberOfAddresses) {
      let address = this.api.generateAddress();
      cscAccounts.push(address);
    }
    
    return cscAccounts;
  }
};

