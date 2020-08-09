export default class VanityWalletStorer {
  
  constructor(serviceEndpoint, walletGenerator, request) {
    this.serviceEndpoint = serviceEndpoint;
    this.walletGenerator = walletGenerator;
    this.request = request;
  }
  
  generateAndStoreVanityWallets() {
      let that = this;
      let wallet = this.walletGenerator.generate();

      this.request.post({
          url: 'http://csc-vanity-wallet-service:8080/wallet',
          json: wallet
      }, function (err, resp) {
        that.generateAndStoreVanityWallets();
      });
  }
}