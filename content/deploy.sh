#!/bin/bash
echo "--------------------------------------"
echo "Deploy Document"
echo "--------------------------------------"
echo ""

DOCUMENT_VERSION_CURRENT=content
DOCUMENT_VERSION=$1

if [ -z != $DOCUMENT_VERSION ]
then
echo "Document version is $DOCUMENT_VERSION"
else
DOCUMENT_VERSION=$DOCUMENT_VERSION_CURRENT
echo "Document version is null, use default $DOCUMENT_VERSION"
fi

#rm -fr $DOCUMENT_VERSION

echo "gitbook generate pages start ..."
gitbook build ./ $DOCUMENT_VERSION
echo "gitbook generate pages done"

echo ""

git status
echo ""
git add --all
echo ""
git commit -m "Deploy document"
echo ""
git push origin gh-pages
