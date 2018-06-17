const MINIMUM_WORD_LENGTH = 3;

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
    
    let isVanityAddress = this.words.filter((word) => word.length >= MINIMUM_WORD_LENGTH).some((word) => {
      let firstThreeLettersOfAddress = address.substring(0, word.length);

      if(firstThreeLettersOfAddress.toUpperCase() === word.toUpperCase()) {
        console.log("Address c" + address + " contains word: " + word + ".");
        return true;
      }
    });
    
    return isVanityAddress;
  }
  
  _removeCSCIdentifierFromAddress(address) {
    return address.substring(1);
  }
};



