export default function generateAddressPayload(address) {
  return {
    secret: 'secret',
    address: address,
    publicKey: 'publicKey'
  };
}