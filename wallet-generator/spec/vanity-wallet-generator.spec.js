import generateWalletPayload from "../test-util/test-wallet-generator.js";
import VanityWalletGenerator from "../src/vanity-wallet-generator.js";

describe('Given the vanity wallet generator', () => {
  describe('with an array of words', () => {
    const NUMBER_OF_WALLETS = 2;
    let arrayOfWords = [];
    
    describe('containing words smaller than the minimum word length specified', () => {
      const MINIMUM_WORD_LENGTH = 4;
      arrayOfWords.push("Banter");
      arrayOfWords.push("Bee");
  
      let wallets = [generateWalletPayload('c' + arrayOfWords[0]), generateWalletPayload('c' + arrayOfWords[1])];
      let cscApi = mockCSCAPI(wallets);
      let testObj = new VanityWalletGenerator(cscApi, arrayOfWords, NUMBER_OF_WALLETS, MINIMUM_WORD_LENGTH);
      
      describe('when the generate function is invoked', () => {
        let wallets = testObj.generate(NUMBER_OF_WALLETS);
        
        it('should return the exact number of wallets specified', () => {
          expect(wallets.length).toBe(NUMBER_OF_WALLETS);
        });
        
        it('should only contain addresses with words that are greater in length than the minimum word length', () => {
          wallets.forEach((wallet) => {
            let walletAddressVanityWord = wallet.address.substring(1, arrayOfWords[0].length + 1);
            expect(walletAddressVanityWord).toEqual(arrayOfWords[0]);
            expect(walletAddressVanityWord.length).not.toBeLessThan(MINIMUM_WORD_LENGTH);
          });
        });
      });
    });
    
  });
});

function mockCSCAPI(addressesToReturn) {
  let callCounter = 0;
  
  return {
    generateAddress: function () {
      callCounter++;
      return callCounter % 2 === 0 ? addressesToReturn[0] : addressesToReturn[1];
    }
  }
}