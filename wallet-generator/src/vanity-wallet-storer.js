export default class VanityWalletStorer {
  
  constructor(serviceEndpoint, walletGenerator, httpRequester, vanityWalletBatchSize) {
    this.serviceEndpoint = serviceEndpoint;
    this.walletGenerator = walletGenerator;
    this.httpRequester = httpRequester;
  }
  
  generateAndStoreVanityWallets() {
    let wallets = this.walletGenerator.generate();
    this.httpRequester.post(this.serviceEndpoint, (error) => {console.error("ERROR: " + error)}).form({"wallets": wallets});
  }
}