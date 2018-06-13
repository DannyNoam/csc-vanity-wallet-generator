import WalletGenerator from "../src/wallet-generator";
import sinon from 'sinon'

describe('Given the wallet generator', () => {
  let cscApi = {
    generateAddress: sinon.spy()
  };
  let testObj = new WalletGenerator(cscApi);
  
  describe('when the generate function is invoked', () => {
    let numberOfAddresses = 5;
    
    testObj.generate(numberOfAddresses);
    
    it('should generate exactly the number of addresses that it was requested to', () => {
      sinon.assert.callCount(cscApi.generateAddress, numberOfAddresses);
    });
  });
});