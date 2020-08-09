export default class VanityWalletStorer {
  
  constructor(serviceEndpoint, walletGenerator, request) {
    this.serviceEndpoint = serviceEndpoint;
    this.walletGenerator = walletGenerator;
    this.request = request;
  }
  
  generateAndStoreVanityWallets() {
      let wallet = this.walletGenerator.generate();
      let that = this;
      let requestBody = JSON.stringify(wallet);

      this.request.post({
          url: 'http://csc-vanity-wallet-service:8080/wallet',
          json: requestBody
      }, function (err, resp) {
        that.generateAndStoreVanityWallets();
      });
  }
}