import VanityWalletStorer from "../src/vanity-address-storer";
import sinon from 'sinon';
import generateAddressPayload from "../test-util/test-address-generator";

describe('Given the vanity wallet storer', () => {
  const serviceEndpoint = "http://service";
  const vanityAddressBatchSize = 3;
  
  let vanityAddresses = generateFakeVanityAddresses();
  let vanityWalletGenerator = { generate: () => { return vanityAddresses; }};
  let httpRequesterPost =  { post: sinon.stub() };
  let httpRequester = { request: httpRequesterPost };
  let httpRequesterForm = { form: sinon.spy() };
  
  httpRequesterPost.post.withArgs(serviceEndpoint).returns(httpRequesterForm);
  
  let testObj = new VanityWalletStorer(serviceEndpoint, vanityWalletGenerator, httpRequester, vanityAddressBatchSize);
  
  describe("when generateInfiniteVanityAddresses is invoked", () => {
    testObj.generateInfiniteVanityAddresses();
    
    it("should POST vanity addresses found using the provided database endpoint", () => {
      let addressesPayload = httpRequesterForm.form.getCalls()[0].args[0];
  
      expect(addressesPayload).toEqual({ addresses: vanityAddresses });
    });
  });
});

function generateFakeVanityAddresses(numberOfAddresses) {
  let fakeVanityAddresses = [];
  
  for(let i = 0; i < numberOfAddresses; i++) {
    fakeVanityAddresses.push(generateAddressPayload("Apple"));
  }
  
  return fakeVanityAddresses;
}