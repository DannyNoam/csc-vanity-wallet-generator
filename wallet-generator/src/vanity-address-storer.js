export default class VanityWalletStorer {
  
  constructor(serviceEndpoint, walletGenerator, httpRequester, vanityAddressBatchSize) {
    this.serviceEndpoint = serviceEndpoint;
    this.walletGenerator = walletGenerator;
    this.httpRequester = httpRequester;
    this.vanityAddressBatchSize = vanityAddressBatchSize;
  }
  
  generateInfiniteVanityAddresses() {
    let addresses = this.walletGenerator.generate(this.vanityAddressBatchSize);
    
    this.httpRequester.request.post(this.serviceEndpoint).form({"addresses": addresses});
  }
}