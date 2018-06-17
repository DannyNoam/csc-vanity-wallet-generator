export default class VanityWalletStorer {
  
  constructor(serviceEndpoint, walletGenerator, httpRequester, vanityWalletBatchSize) {
    this.serviceEndpoint = serviceEndpoint;
    this.walletGenerator = walletGenerator;
    this.httpRequester = httpRequester;
    this.vanityWalletBatchSize = vanityWalletBatchSize;
  }
  
  generateInfiniteVanityWallets() {
    let wallets = this.walletGenerator.generate(this.vanityWalletBatchSize);
    
    this.httpRequester.request.post(this.serviceEndpoint).form({"wallets": wallets});
  }
}