VERSION=$1

WALLET_GENERATOR_IMAGE_NAME=wallet-generator
WALLET_SERVICE_IMAGE_NAME=wallet-service

WALLET_GENERATOR_VERSIONED_IMAGE_NAME=${WALLET_GENERATOR_IMAGE_NAME}:b${VERSION}
WALLET_SERVICE_VERSIONED_IMAGE_NAME=${WALLET_SERVICE_IMAGE_NAME}:b${VERSION}

WALLET_GENERATOR_REPOSITORY_NAME=docker.io/whufc4life1/csc-vanity-wallet-generator
WALLET_SERVICE_REPOSITORY_NAME=docker.io/whufc4life1/csc-vanity-wallet-service

# Both the Wallet Generator and Wallet Service are built in lock-step, in regards to versioning

if [ -z "$VERSION" ]
then
    echo "You need to pass the version as the first argument to the build script"
    exit 1
fi

# Build and tag wallet generator
echo "Building wallet generator!"
cd wallet-generator && docker build -t ${WALLET_GENERATOR_VERSIONED_IMAGE_NAME} .

docker tag "${WALLET_GENERATOR_VERSIONED_IMAGE_NAME}" "${WALLET_GENERATOR_REPOSITORY_NAME}:b${VERSION}"
docker tag "${WALLET_GENERATOR_VERSIONED_IMAGE_NAME}" "${WALLET_GENERATOR_REPOSITORY_NAME}:latest"

# Build and tag wallet service
echo "Building wallet service!"

cd ../wallet-service && mvn clean install && docker build -t ${WALLET_SERVICE_VERSIONED_IMAGE_NAME} .

docker tag "${WALLET_SERVICE_VERSIONED_IMAGE_NAME}" "${WALLET_SERVICE_REPOSITORY_NAME}:b${VERSION}"
docker tag "${WALLET_SERVICE_VERSIONED_IMAGE_NAME}" "${WALLET_SERVICE_REPOSITORY_NAME}:latest"

docker push ${WALLET_GENERATOR_REPOSITORY_NAME}:b${VERSION}
docker push ${WALLET_GENERATOR_REPOSITORY_NAME}:latest

docker push ${WALLET_SERVICE_REPOSITORY_NAME}:b${VERSION}
docker push ${WALLET_SERVICE_REPOSITORY_NAME}:latest