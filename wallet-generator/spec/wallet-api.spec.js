import WalletAPI from '../src/wallet-api';
import generateWalletPayload from "../test-util/test-wallet-generator";

describe('Given the wallet API', () => {
  let walletPayload = generateWalletPayload();
  let apiImplementation = {
    generateAddress: () => { return walletPayload }
  };
  let testObj = new WalletAPI(apiImplementation);
  
  describe('when generateAddress is invoked', () => {
    let actual = testObj.generateAddress();
    
    it('should return call the generateAddress function of the underlying API', () => {
      expect(actual).toEqual(walletPayload);
    })
  });
});