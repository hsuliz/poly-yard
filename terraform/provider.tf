terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "~> 2.0"
    }
    kubectl = {
      source  = "gavinbunney/kubectl"
      version = ">= 1.7.0"
    }
  }
}


provider "digitalocean" {
  token = var.do_token
}

provider "kubectl" {
  host                   = digitalocean_kubernetes_cluster.poly-yard-app.endpoint
  cluster_ca_certificate = base64decode(
    digitalocean_kubernetes_cluster.poly-yard-app.kube_config[
    0
    ].cluster_ca_certificate
  )
  token            = digitalocean_kubernetes_cluster.poly-yard-app.kube_config[0].token
  load_config_file = false
}
