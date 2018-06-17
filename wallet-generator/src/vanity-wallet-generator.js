export default class VanityWalletGenerator {
  
  constructor(api, words) {
    this.api = api;
    this.words = words;
  }
  
  generate(numberOfWallets) {
    var cscAccounts = [];
  
    while (cscAccounts.length < numberOfWallets) {
      let wallet = this.api.generateAddress();
      
      if(this._isVanityWallet(wallet.address)) {
        cscAccounts.push(wallet);
      }
    }
    
    return cscAccounts;
  }
  
  _isVanityWallet(address) {
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



