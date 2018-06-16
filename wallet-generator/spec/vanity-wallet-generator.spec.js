import VanityWalletGenerator from "../src/vanity-wallet-generator";

describe('Given the wallet generator', () => {
  let CALL_COUNTER = 0;
  let arrayOfWords = ['Apple', 'Banana', 'Pear'];
  
  let nonVanityAddressPayload = generateAddressPayload("address");
  let vanityAddressPayload = generateAddressPayload('c' + arrayOfWords[0] + 'address');
  
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
    
    it('should generate exactly the number of addresses that it was requested to', () => {
      expect(addresses.length).toBe(2);
      expect(addresses).toEqual([vanityAddressPayload, vanityAddressPayload]);
    });
  });
});

function generateAddressPayload(address) {
  return {
    secret: 'secret',
    address: address,
    publicKey: 'publicKey'
  };
}