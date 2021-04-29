#!/bin/bash


#
# This script is *not* recompiling the storycards!
#


# define paths and filenames
full_path=$(realpath $0)
dir_path=$(dirname $full_path)

anforderungen_path=$dir_path"/anforderungen"
anforderungen_tex="/anforderungen.tex"
anforderungen_pdf="/anforderungen.pdf"


architektur_path=$dir_path"/architektur"
architektur_tex="/architektur.tex"
architektur_pdf="/architektur.pdf"


target=$(realpath $dir_path"/..")


echo
echo "compiling latex documents"
cd $architektur_path
latexmk -pdf "."$architektur_tex
latexmk -c

cd $anforderungen_path
latexmk -pdf "."$anforderungen_path
latexmk -c

echo
echo "moving documents to target directory"
mv -v $anforderungen_path$anforderungen_pdf $target$anforderungen_pdf

echo
mv -v $architektur_path$architektur_pdf $target$architektur_pdf

