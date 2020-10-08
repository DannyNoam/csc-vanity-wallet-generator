export default class VanityWalletGenerator {
  
  constructor(api, words, minimumWordLength) {
    this.api = api;
    this.words = words;
    this.minimumWordLength = minimumWordLength;
  }
  
  generate() {
    let wallet = this.api.generateAddress();
    let vanityWallet = this._checkForVanityWallet(wallet.address);

    if(vanityWallet != undefined && vanityWallet.isVanityWallet) {
      wallet.word = vanityWallet.word;
      return wallet;
    }

    return this.generate();
  }
  
  _checkForVanityWallet(address) {
    address = this._removeCSCIdentifierFromAddress(address);
    let vanityWord = null;

    const startTime = new Date().getTime();

    let result = this.words.filter((word) => word.length >= this.minimumWordLength).some((word) => {
      let firstXLettersOfAddress = address.substring(0, word.length);

      if(firstXLettersOfAddress.toUpperCase() === word.toUpperCase()) {
        vanityWord = word;

        console.log("FOUND: Address c" + address + " contains word: " + word + ".");

        return true;
      }
    });

    const endTime = new Date().getTime();
    console.log("Executed vanity check, timeTakenMs=" + (endTime-startTime));

    return result === true ? { isVanityWallet: true, word: vanityWord } : { isVanityWallet: false };
  }
  
  _removeCSCIdentifierFromAddress(address) {
    return address.substring(1);
  }
};



