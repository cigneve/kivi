{
  description = "kivi";

  inputs = {
    nixpkgs.url = "nixpkgs/unstable";
  };

  outputs = {nixpkgs, ...}: let
    inherit (nixpkgs) lib;
    systems = [
      "x86_64-linux"
    ];
    forEachSystem = lib.genAttrs systems;
  in {
    devShells = forEachSystem (system: let
      pkgs = nixpkgs.legacyPackages.${system};
    in {
      default = pkgs.mkShell {
        buildInputs = with pkgs; [
          docker
          docker-compose
        ];
      };
    });
  };
}
