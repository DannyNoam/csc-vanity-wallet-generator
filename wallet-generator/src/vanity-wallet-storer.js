export default class VanityWalletStorer {
  
  constructor(serviceEndpoint, walletGenerator, request) {
    this.serviceEndpoint = serviceEndpoint;
    this.walletGenerator = walletGenerator;
    this.request = request;
  }
  
  generateAndStoreVanityWallets() {
      let wallet = null;
      let that = this;

      while(wallet == null) {
          wallet = this.walletGenerator.generate();
      }

      var needle = require('needle');
      needle.post('http://localhost:8080/wallets', wallet, {json: true}, function (err, resp) {
        that.generateAndStoreVanityWallets();
      });
  }
}