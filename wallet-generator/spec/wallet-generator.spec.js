import WalletGenerator from "../src/wallet-generator";

describe('Given the wallet generator', () => {
  let mockAddressPayload = {
    secret: 'secret',
    address: 'address',
    publicKey: 'publicKey'
  };
  
  let cscApi = {
    generateAddress: function () {
      return mockAddressPayload;
    }
  };
  let testObj = new WalletGenerator(cscApi);
  
  describe('when the generate function is invoked', () => {
    let numberOfAddresses = 3;
    
    let addresses = testObj.generate(numberOfAddresses);
    
    it('should generate exactly the number of addresses that it was requested to', () => {
      expect(addresses.length).toBe(3);
      expect(addresses).toEqual([mockAddressPayload, mockAddressPayload, mockAddressPayload]);
    });
  });
});