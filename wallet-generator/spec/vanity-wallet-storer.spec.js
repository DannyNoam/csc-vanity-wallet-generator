import VanityWalletStorer from "../src/vanity-wallet-storer";
import sinon from 'sinon';
import generateWalletPayload from "../test-util/test-wallet-generator";

describe('Given the vanity wallet storer', () => {
  const serviceEndpoint = "http://service";
  const vanityWalletBatchSize = 3;
  
  let vanityWallets = generateFakeVanityWallets();
  let vanityWalletGenerator = { generate: () => { return vanityWallets; }};
  let httpRequesterPost =  { post: sinon.stub() };
  let httpRequester = { request: httpRequesterPost };
  let httpRequesterForm = { form: sinon.spy() };
  
  httpRequesterPost.post.withArgs(serviceEndpoint).returns(httpRequesterForm);
  
  let testObj = new VanityWalletStorer(serviceEndpoint, vanityWalletGenerator, httpRequester, vanityWalletBatchSize);
  
  describe("when generateInfiniteVanityWallets is invoked", () => {
    testObj.generateInfiniteVanityWallets();
    
    it("should POST vanity wallets found using the provided database endpoint", () => {
      let walletsPayload = httpRequesterForm.form.getCalls()[0].args[0];
  
      expect(walletsPayload).toEqual({ wallets: vanityWallets });
    });
  });
});

function generateFakeVanityWallets(numberOfAddresses) {
  let fakeVanityAddresses = [];
  
  for(let i = 0; i < numberOfAddresses; i++) {
    fakeVanityAddresses.push(generateAddressPayload("Apple"));
  }
  
  return fakeVanityAddresses;
}