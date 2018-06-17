export default function generateWalletPayload(address) {
  return {
    secret: 'secret',
    address: address,
    publicKey: 'publicKey'
  };
}