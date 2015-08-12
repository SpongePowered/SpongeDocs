# Not pure yet because some of the necessary dependencies are not packaged. However, it's a quick way to activate deps that are normally systemwide
# To use: nix-shell etc/env.nix
let
 pkgs = import <nixpkgs> {};
 stdenv = pkgs.stdenv;
in
with pkgs; rec {
  docs = stdenv.mkDerivation {
    name = "SpongeDocs";
    src = ./.;
    buildInputs = [pkgs.nodejs nodePackages.grunt-cli python34Packages.sphinx ];
  };
}
