export default class WalletAPI {
  
  constructor(api) {
    this.api = api;
  }
  
  generateAddress() {
    return this.api.generateAddress();
  }
}