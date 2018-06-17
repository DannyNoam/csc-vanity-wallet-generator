import VanityWalletGenerator from "../src/vanity-wallet-generator";
import generateWalletPayload from "../test-util/test-wallet-generator";

describe('Given the vanity wallet generator', () => {
  let CALL_COUNTER = 0;
  let arrayOfWords = ['Apple', 'Banana', 'Pear'];
  
  let nonVanityAddressPayload = generateWalletPayload("wallet");
  let vanityAddressPayload = generateWalletPayload('c' + arrayOfWords[0] + 'address');
  
  let cscApi = {
    generateAddress: function () {
      CALL_COUNTER++;
      return CALL_COUNTER % 2 === 0   ? nonVanityAddressPayload : vanityAddressPayload;
    }
  };
  
  let testObj = new VanityWalletGenerator(cscApi, arrayOfWords);
  
  describe('when the generate function is invoked', () => {
    let numberOfAddresses = 2;
    
    let addresses = testObj.generate(numberOfAddresses);
    
    it('should generate exactly the number of wallets that it was requested to', () => {
      expect(addresses.length).toBe(2);
      expect(addresses).toEqual([vanityAddressPayload, vanityAddressPayload]);
    });
  });
});