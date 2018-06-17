export default class VanityWalletStorer {
  
  constructor(serviceEndpoint, walletGenerator, httpRequester, vanityWalletBatchSize) {
    this.serviceEndpoint = serviceEndpoint;
    this.walletGenerator = walletGenerator;
    this.httpRequester = httpRequester;
    this.vanityWalletBatchSize = vanityWalletBatchSize;
  }
  
  generateAndStoreVanityWallets() {
    let wallets = this.walletGenerator.generate(this.vanityWalletBatchSize);
    this.httpRequester.post(this.serviceEndpoint, (error) => {console.error("ERROR: " + error)}).form({"wallets": wallets});
  }
}