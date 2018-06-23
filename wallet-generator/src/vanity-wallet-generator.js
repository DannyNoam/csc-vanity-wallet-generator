export default class VanityWalletGenerator {
  
  constructor(api, words, numberOfWallets, minimumWordLength) {
    this.api = api;
    this.words = words;
    this.numberOfWallets = numberOfWallets;
    this.minimumWordLength = minimumWordLength;
  }
  
  generate() {
    var cscAccounts = [];
  
    while (cscAccounts.length < this.numberOfWallets) {
      let wallet = this.api.generateAddress();
      
      if(this._isVanityWallet(wallet.address)) {
        cscAccounts.push(wallet);
      }
    }
    
    return cscAccounts;
  }
  
  _isVanityWallet(address) {
    address = this._removeCSCIdentifierFromAddress(address);
    
    let isVanityAddress = this.words.filter((word) => word.length >= this.minimumWordLength).some((word) => {
      let firstXLettersOfAddress = address.substring(0, word.length);

      if(firstXLettersOfAddress.toUpperCase() === word.toUpperCase()) {
        return true;
      }
    });
    
    return isVanityAddress;
  }
  
  _removeCSCIdentifierFromAddress(address) {
    return address.substring(1);
  }
};



