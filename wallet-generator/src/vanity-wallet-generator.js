// const CasinocoinAPI = require('casinocoin-libjs').CasinocoinAPI
// const api = new CasinocoinAPI({ server: 'wss://ws01.casinocoin.org:4443' })
export default class VanityWalletGenerator {
  
  constructor(api, words) {
    this.api = api;
    this.words = words;
  }
  
  generate(numberOfAddresses) {
    var cscAccounts = [];
  
    while (cscAccounts.length < numberOfAddresses) {
      let address = this.api.generateAddress();
      
      if(this._isVanityAddress(address.address)) {
        cscAccounts.push(address);
      }
    }
    
    return cscAccounts;
  }
  
  _isVanityAddress(address) {
    address = this._removeCSCIdentifierFromAddress(address);
    
    let isVanityAddress = this.words.some((word) => {
      address = address.substring(0, word.length);

      if(address.toUpperCase() === word.toUpperCase()) {
        return true;
      }
    });
    
    return isVanityAddress;
  }
  
  _removeCSCIdentifierFromAddress(address) {
    return address.substring(1);
  }
};



